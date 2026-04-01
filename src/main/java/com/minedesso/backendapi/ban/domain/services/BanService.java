package com.minedesso.backendapi.ban.domain.services;

import com.minedesso.backendapi.ban.domain.dtos.in.BanSaveCommand;
import com.minedesso.backendapi.ban.domain.dtos.out.Ban;
import com.minedesso.backendapi.ban.domain.dtos.in.MojangPlayer;
import com.minedesso.backendapi.ban.domain.util.exceptions.PlayerAlreadyBannedException;
import com.minedesso.backendapi.ban.domain.util.exceptions.PlayerUuidNotFoundException;
import com.minedesso.backendapi.ban.domain.util.exceptions.ReasonNotFoundException;
import com.minedesso.backendapi.ban.domain.util.properties.MojangApiPropertyConfig;
import com.minedesso.backendapi.ban.persistence.BanEntity;
import com.minedesso.backendapi.ban.persistence.ReasonEntity;
import com.minedesso.backendapi.ban.persistence.ReasonRepository;
import com.minedesso.backendapi.home.domain.utils.exceptions.ActiveBanNotFoundException;
import com.minedesso.backendapi.minecraftplayer.domain.dtos.in.MinecraftPlayerSaveCommand;
import com.minedesso.backendapi.minecraftplayer.domain.services.MinecraftPlayerService;
import com.minedesso.backendapi.minecraftplayer.domain.utils.exceptions.MinecraftPlayerNotFoundException;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerEntity;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BanService implements BanUseCase {
    private final ReasonRepository reasonRepository;
    private final MinecraftPlayerRepository minecraftPlayerRepository;
    private final MinecraftPlayerService minecraftPlayerService;
    private final MojangApiPropertyConfig mojangApiPropertyConfig;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Ban saveBan(BanSaveCommand banSaveCommand)
            throws ReasonNotFoundException, PlayerAlreadyBannedException, ActiveBanNotFoundException, PlayerUuidNotFoundException {
        Optional<ReasonEntity> reasonEntityOptional = reasonRepository.findById(banSaveCommand.getReasonId());
        if (reasonEntityOptional.isEmpty()) throw new ReasonNotFoundException(banSaveCommand.getReasonId());

        MinecraftPlayerEntity targetPlayer = getTargetPlayer(banSaveCommand);
        MinecraftPlayerEntity senderPlayer = getBannedBy(banSaveCommand);

        if (isPlayerBanned(targetPlayer))
            throw new PlayerAlreadyBannedException(banSaveCommand.getTargetName());
        BanEntity banEntity = new BanEntity(banSaveCommand, reasonEntityOptional.get());

        banEntity.setBannedBy(senderPlayer);
        targetPlayer.addBan(banEntity);
        minecraftPlayerRepository.save(targetPlayer);
        return getBanDetails(targetPlayer);
    }

    @Override
    public Ban getBanDetails(UUID uuid) throws ActiveBanNotFoundException {
        MinecraftPlayerEntity playerEntity = minecraftPlayerRepository.findById(uuid)
                .orElseThrow(() -> new IllegalStateException("Player not found"));

        return getBanDetails(playerEntity);
    }

    @Override
    public boolean checkActiveBan(UUID uuid) {
        MinecraftPlayerEntity playerEntity = minecraftPlayerRepository.findById(uuid)
                .orElseThrow(() -> new IllegalStateException("Player not found"));

        return isPlayerBanned(playerEntity);
    }

    @Override
    public boolean checkActiveBan(String playerName) throws MinecraftPlayerNotFoundException {
        MinecraftPlayerEntity playerEntity = minecraftPlayerRepository.findByName(playerName)
                .orElseThrow(() -> new MinecraftPlayerNotFoundException(playerName));

        return isPlayerBanned(playerEntity);
    }

    private boolean isPlayerBanned(MinecraftPlayerEntity player) {
        List<BanEntity> activeBans = player.getBans().stream()
                .filter(BanEntity::isActive)
                .toList();
        return !activeBans.isEmpty();
    }

    private Ban getBanDetails(MinecraftPlayerEntity playerEntity) throws ActiveBanNotFoundException {
        List<BanEntity> activeBans = playerEntity.getBans().stream()
                .filter(BanEntity::isActive)
                .toList();

        if (activeBans.isEmpty()) throw new ActiveBanNotFoundException();

        BanEntity banEntity = activeBans.getFirst();
        return new Ban(banEntity);
    }

    private UUID getPlayerUUIDFromMojangAPI(String playerName) {
        try {
            String url = String.format(mojangApiPropertyConfig.getPlayerUuidUrl(), playerName);

            RestClient restClient = RestClient.builder()
                    .baseUrl(url)
                    .build();

            MojangPlayer mojangPlayer = restClient.get()
                    .retrieve()
                    .body(MojangPlayer.class);

            if(mojangPlayer == null) return null;
            return mojangPlayer.toUUID();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to lookup player uuid from Mojang API", e);
        }
    }

    private MinecraftPlayerEntity handlePlayerNotFound(String playerName) throws PlayerUuidNotFoundException {
        UUID targetUuid = getPlayerUUIDFromMojangAPI(playerName);
        if (targetUuid == null) throw new PlayerUuidNotFoundException(playerName);

        MinecraftPlayerSaveCommand minecraftPlayerSaveCommand = new MinecraftPlayerSaveCommand(
                targetUuid,
                playerName,
                false
        );

        return minecraftPlayerService.save(minecraftPlayerSaveCommand);
    }

    private MinecraftPlayerEntity getBannedBy(BanSaveCommand banSaveCommand) {
        if(banSaveCommand.getBannedBy() == null) return null;
        return minecraftPlayerRepository.findById(banSaveCommand.getBannedBy())
                .orElseThrow(() -> new IllegalStateException("Sender not found"));
    }

    private MinecraftPlayerEntity getTargetPlayer(BanSaveCommand banSaveCommand) throws PlayerUuidNotFoundException {
        Optional<MinecraftPlayerEntity> targetPlayerOptional = minecraftPlayerRepository
                .findByNameIgnoreCase(banSaveCommand.getTargetName());

        if (targetPlayerOptional.isEmpty()) {
            return handlePlayerNotFound(banSaveCommand.getTargetName());
        } else {
            return targetPlayerOptional.get();
        }
    }
}
