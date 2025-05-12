INSERT INTO subscriptions (service_name, service_url)
VALUES
    ('YouTube Premium', 'https://youtube.com/premium'),
    ('Netflix', 'https://netflix.com'),
    ('Spotify', 'https://spotify.com'),
    ('VK Музыка', 'https://vk.com/vkmusic'),
    ('Яндекс.Плюс', 'https://plus.yandex.ru')
    ON CONFLICT DO NOTHING;