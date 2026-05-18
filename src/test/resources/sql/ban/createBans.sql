insert into ban (id, created_at, expires_at, is_active, reason_id, banned_by_uuid, target_uuid)
values (1,
        '2024-01-01 00:00:00',
        '2024-12-31 23:59:59',
        true,
        1,
        'a22e9e92-1894-4d63-993c-a09f0e1edc6f',
        'a22e9e92-1894-4d63-993c-a09f0e1edc7f');