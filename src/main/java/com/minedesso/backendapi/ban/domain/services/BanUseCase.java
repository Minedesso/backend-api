package com.minedesso.backendapi.ban.domain.services;

import com.minedesso.backendapi.ban.domain.dtos.in.BanSaveCommand;
import com.minedesso.backendapi.ban.domain.dtos.out.Ban;
import com.minedesso.backendapi.ban.domain.util.exceptions.PlayerAlreadyBannedException;
import com.minedesso.backendapi.ban.domain.util.exceptions.PlayerUuidNotFoundException;
import com.minedesso.backendapi.ban.domain.util.exceptions.ReasonNotFoundException;
import com.minedesso.backendapi.home.domain.utils.exceptions.ActiveBanNotFoundException;
import com.minedesso.backendapi.minecraftplayer.domain.utils.exceptions.MinecraftPlayerNotFoundException;

import java.util.UUID;

public interface BanUseCase {

    Ban saveBan(BanSaveCommand banSaveCommand)
            throws ReasonNotFoundException, PlayerAlreadyBannedException, ActiveBanNotFoundException, PlayerUuidNotFoundException;

    Ban getBanDetails(UUID uuid) throws ActiveBanNotFoundException;

    boolean checkActiveBan(UUID uuid);

    boolean checkActiveBan(String playerName) throws MinecraftPlayerNotFoundException;

}
