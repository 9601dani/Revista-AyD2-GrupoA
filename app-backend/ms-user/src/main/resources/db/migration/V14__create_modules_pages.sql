INSERT INTO modules(name, path)
VALUES
    ('user', '/user'),
    ('magazines', '/magazines'),
    ('editor', '/editor'),
    ('admin', '/admin');

INSERT INTO pages(name, FK_Module, path)
VALUES
    ('Mi Perfil', 1, '/profile'),
    ('Mis suscripciones', 1, '/my-subscriptions'),
    ('Mi Cartera', 1, '/my-bill'),
    ('Comprar anuncio', 1, '/buy-add'),
    ('Mis anuncios', 1, '/my-adds'),
    ('Buscar Revistas', 2, '/search'),
    ('Ver Revista', 2, '/view'),
    ('Nueva revista', 3, '/new-magazine'),
    ('Editar revista', 3, '/update-magazine'),
    ('Mis revistas', 3, '/my-magazines'),
    ('Ajustes', 4, '/settings'),
    ('Administrar Anuncios', 4, '/manage-adds');