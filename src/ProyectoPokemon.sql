/*Base de datos Proyecto Pokemon del equipo DAMachuPIKAchu Ivan Jesus Corbalan Pascual, Sergio Martinez Alonso, Lorenzo Fernandez Jara 1ºDAM*/

-- Si la tabla ya existe, la eliminamos
DROP TABLE IF EXISTS Pokemon;
DROP TABLE IF EXISTS Movimiento;
DROP TABLE IF EXISTS Mochila;
DROP TABLE IF EXISTS Objetos;
DROP TABLE IF EXISTS Pokedex;
DROP TABLE IF EXISTS Entrenadores;

-- Creación tabla Objetos
CREATE TABLE Objetos (
    id_objeto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    efecto TEXT,
    descripcion TEXT,
    precio DECIMAL(10,2)
);

-- Inserción de datos a tabla Objetos
INSERT INTO Objetos (nombre, efecto, descripcion, precio) VALUES
    ('pesa', 'Aumenta la fuerza', 'Objeto pesado para entrenamiento', 50.00),
    ('pluma', 'Ligereza', 'Reduce el peso del usuario', 50.00),
    ('chaleco', 'Defensa', 'Aumenta la protección contra golpes', 150.00),
    ('baston', 'Soporte', 'Ayuda en el equilibrio y caminatas', 25.00),
    ('pilas', 'Energía', 'Recarga dispositivos eléctricos', 20.00),
    ('eter', 'Regeneración', 'Restaura puntos de pp', 200.00),
    ('anillo único', 'Invisibilidad', 'Hace al pokemon invisible', 9999.99),
    ('pokeball', 'Captura', 'Permite capturar pokemons', 50.00);

-- Creación tabla Entrenadores con login
CREATE TABLE Entrenadores (
    id_entrenador INT PRIMARY KEY AUTO_INCREMENT,
    usuario VARCHAR(50) UNIQUE NOT NULL,
    contraseña VARCHAR(100) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    equipo_principal VARCHAR(255),
    equipo_secundario VARCHAR(255),
    pokedollars INT DEFAULT 0,
    pokeballs INT DEFAULT 100,
    objetos TEXT
);

/*Creacion tabla mochila*/
CREATE TABLE Mochila ( 
    id_entrenador INT, 
    id_objeto INT, 
    cantidad INT NOT NULL, 
    PRIMARY KEY (id_entrenador, id_objeto), 
); 

/*Constantes*/
ALTER TABLE Mochila
ADD CONSTRAINT fk_mochila_entrenador
FOREIGN KEY (id_entrenador) REFERENCES Entrenadores(id_entrenador);

ALTER TABLE Mochila
ADD CONSTRAINT fk_mochila_objeto
FOREIGN KEY (id_objeto) REFERENCES Objetos(id_objeto);

/*Insercion de datos a tabla entrenadores*/
INSERT INTO Entrenadores (usuario, contraseña, nombre, equipo_principal, equipo_secundario, pokedollars, objetos)
VALUES
    ('luisre', '1234', 'Luis Regino', 'Pikachu, Charizard, Bulbasaur, Squirtle, Pidgeotto, Muk', 'Snorlax, Kingler, Tauros, Lapras, Heracross, Donphan', 5000, 'Pokeball, Superpoción'),
    ('fer', 'abcd', 'Fernando', 'Onix, Geodude, Vulpix, Ludicolo, Steelix, Croagunk', 'Zubat, Crobat, Marshtomp, Forretress, Sudowoodo, Chansey', 3000, 'Hiperpoción, Revivir'),
    ('josec', 'pass', 'Jose Carrion', 'Starmie, Staryu, Psyduck, Gyarados, Golduck, Politoed', 'Horsea, Togepi, Seaking, Corsola, Lanturn, Azumarill', 4500, 'Piedra Agua, Poción'
);


/*Creacion tabla pokedex*/
CREATE TABLE Pokedex (
    num_pokedex INT PRIMARY KEY,
    nom_pokemon VARCHAR(20) NOT NULL,
    img_frontal VARCHAR(100) NOT NULL, 
    img_trasera VARCHAR(100) NOT NULL, 
    sonido VARCHAR(100) NOT NULL, 
    nivel_evolucion INT,
    tipo1 VARCHAR(20) NOT NULL CHECK (tipo1 IN ('Normal', 'Fuego', 'Agua', 'Planta', 'Eléctrico', 'Hielo', 'Lucha', 'Veneno', 'Tierra', 'Volador', 'Psíquico', 'Bicho', 'Roca', 'Fantasma', 'Dragón', 'Siniestro', 'Acero', 'Hada')), 
    tipo2 VARCHAR(20) CHECK (tipo2 IN ('Normal', 'Fuego', 'Agua', 'Planta', 'Eléctrico', 'Hielo', 'Lucha', 'Veneno', 'Tierra', 'Volador', 'Psíquico', 'Bicho', 'Roca', 'Fantasma', 'Dragón', 'Siniestro', 'Acero', 'Hada'))  
) ENGINE=InnoDB;


/*Creacion tabla pokemon*/
CREATE TABLE Pokemon (
    id_pokemon INT AUTO_INCREMENT PRIMARY KEY,
    id_entrenador INT,
    num_pokedex INT,
    nombre VARCHAR(100) NOT NULL,
    mote VARCHAR(100),
    vitalidad INT NOT NULL,
    ataque INT NOT NULL,
    defensa INT NOT NULL,
    ataque_especial INT NOT NULL,
    defensa_especial INT NOT NULL,
    velocidad INT NOT NULL,
    nivel INT NOT NULL,
    movimientos TEXT,
    fertilidad INT,
    sexo ENUM('H', 'M') NOT NULL,
    tipo1 VARCHAR(50) NOT NULL,
    tipo2 VARCHAR(50),
    estado VARCHAR(100),
    equipo INT CHECK (equipo >= 0),
    
    FOREIGN KEY (id_entrenador) REFERENCES Entrenadores(id_entrenador) ON DELETE CASCADE,
    FOREIGN KEY (num_pokedex) REFERENCES Pokedex(num_pokedex) ON DELETE CASCADE
) ENGINE=InnoDB;

INSERT INTO Pokedex (num_pokedex, nom_pokemon, img_frontal, img_trasera, sonido, nivel_evolucion, tipo1, tipo2)
VALUES
(1, 'Bulbasaur', 'bulbasaur_front.png', 'bulbasaur_back.png', 'bulbasaur.ogg', 1, 'Planta', 'Veneno'),
(2, 'Ivysaur', 'ivysaur_front.png', 'ivysaur_back.png', 'ivysaur.ogg', 2, 'Planta', 'Veneno'),
(3, 'Venusaur', 'venusaur_front.png', 'venusaur_back.png', 'venusaur.ogg', 3, 'Planta', 'Veneno'),
(4, 'Charmander', 'charmander_front.png', 'charmander_back.png', 'charmander.ogg', 1, 'Fuego', NULL),
(5, 'Charmeleon', 'charmeleon_front.png', 'charmeleon_back.png', 'charmeleon.ogg', 2, 'Fuego', NULL),
(6, 'Charizard', 'charizard_front.png', 'charizard_back.png', 'charizard.ogg', 3, 'Fuego', 'Volador'),
(7, 'Squirtle', 'squirtle_front.png', 'squirtle_back.png', 'squirtle.ogg', 1, 'Agua', NULL),
(8, 'Wartortle', 'wartortle_front.png', 'wartortle_back.png', 'wartortle.ogg', 2, 'Agua', NULL),
(9, 'Blastoise', 'blastoise_front.png', 'blastoise_back.png', 'blastoise.ogg', 3, 'Agua', NULL),
(10, 'Caterpie', 'caterpie_front.png', 'caterpie_back.png', 'caterpie.ogg', 1, 'Bicho', NULL),
(11, 'Metapod', 'metapod_front.png', 'metapod_back.png', 'metapod.ogg', 2, 'Bicho', NULL),
(12, 'Butterfree', 'butterfree_front.png', 'butterfree_back.png', 'butterfree.ogg', 3, 'Bicho', 'Volador'),
(13, 'Weedle', 'weedle_front.png', 'weedle_back.png', 'weedle.ogg', 1, 'Bicho', 'Veneno'),
(14, 'Kakuna', 'kakuna_front.png', 'kakuna_back.png', 'kakuna.ogg', 2, 'Bicho', 'Veneno'),
(15, 'Beedrill', 'beedrill_front.png', 'beedrill_back.png', 'beedrill.ogg', 3, 'Bicho', 'Veneno'),
(16, 'Pidgey', 'pidgey_front.png', 'pidgey_back.png', 'pidgey.ogg', 1, 'Normal', 'Volador'),
(17, 'Pidgeotto', 'pidgeotto_front.png', 'pidgeotto_back.png', 'pidgeotto.ogg', 2, 'Normal', 'Volador'),
(18, 'Pidgeot', 'pidgeot_front.png', 'pidgeot_back.png', 'pidgeot.ogg', 3, 'Normal', 'Volador'),
(19, 'Rattata', 'rattata_front.png', 'rattata_back.png', 'rattata.ogg', 1, 'Normal', NULL),
(20, 'Raticate', 'raticate_front.png', 'raticate_back.png', 'raticate.ogg', 2, 'Normal', NULL),
(21, 'Spearow', 'spearow_front.png', 'spearow_back.png', 'spearow.ogg', 1, 'Normal', 'Volador'),
(22, 'Fearow', 'fearow_front.png', 'fearow_back.png', 'fearow.ogg', 2, 'Normal', 'Volador'),
(23, 'Ekans', 'ekans_front.png', 'ekans_back.png', 'ekans.ogg', 1, 'Veneno', NULL),
(24, 'Arbok', 'arbok_front.png', 'arbok_back.png', 'arbok.ogg', 2, 'Veneno', NULL),
(25, 'Pikachu', 'pikachu_front.png', 'pikachu_back.png', 'pikachu.ogg', 1, 'Eléctrico', NULL),
(26, 'Raichu', 'raichu_front.png', 'raichu_back.png', 'raichu.ogg', 2, 'Eléctrico', NULL),
(27, 'Sandshrew', 'sandshrew_front.png', 'sandshrew_back.png', 'sandshrew.ogg', 1, 'Tierra', NULL),
(28, 'Sandslash', 'sandslash_front.png', 'sandslash_back.png', 'sandslash.ogg', 2, 'Tierra', NULL),
(29, 'Nidoran♀', 'nidoranf_front.png', 'nidoranf_back.png', 'nidoranf.ogg', 1, 'Veneno', NULL),
(30, 'Nidorina', 'nidorina_front.png', 'nidorina_back.png', 'nidorina.ogg', 2, 'Veneno', NULL),
(31, 'Nidoqueen', 'nidoqueen_front.png', 'nidoqueen_back.png', 'nidoqueen.ogg', 3, 'Veneno', 'Tierra'),
(32, 'Nidoran♂', 'nidoranm_front.png', 'nidoranm_back.png', 'nidoranm.ogg', 1, 'Veneno', NULL),
(33, 'Nidorino', 'nidorino_front.png', 'nidorino_back.png', 'nidorino.ogg', 2, 'Veneno', NULL),
(34, 'Nidoking', 'nidoking_front.png', 'nidoking_back.png', 'nidoking.ogg', 3, 'Veneno', 'Tierra'),
(35, 'Clefairy', 'clefairy_front.png', 'clefairy_back.png', 'clefairy.ogg', 1, 'Hada', NULL),
(36, 'Clefable', 'clefable_front.png', 'clefable_back.png', 'clefable.ogg', 2, 'Hada', NULL),
(37, 'Vulpix', 'vulpix_front.png', 'vulpix_back.png', 'vulpix.ogg', 1, 'Fuego', NULL),
(38, 'Ninetales', 'ninetales_front.png', 'ninetales_back.png', 'ninetales.ogg', 2, 'Fuego', NULL),
(39, 'Jigglypuff', 'jigglypuff_front.png', 'jigglypuff_back.png', 'jigglypuff.ogg', 1, 'Normal', 'Hada'),
(40, 'Wigglytuff', 'wigglytuff_front.png', 'wigglytuff_back.png', 'wigglytuff.ogg', 2, 'Normal', 'Hada'),
(41, 'Zubat', 'zubat_front.png', 'zubat_back.png', 'zubat.ogg', 1, 'Veneno', 'Volador'),
(42, 'Golbat', 'golbat_front.png', 'golbat_back.png', 'golbat.ogg', 2, 'Veneno', 'Volador'),
(43, 'Oddish', 'oddish_front.png', 'oddish_back.png', 'oddish.ogg', 1, 'Planta', 'Veneno'),
(44, 'Gloom', 'gloom_front.png', 'gloom_back.png', 'gloom.ogg', 2, 'Planta', 'Veneno'),
(45, 'Vileplume', 'vileplume_front.png', 'vileplume_back.png', 'vileplume.ogg', 3, 'Planta', 'Veneno'),
(46, 'Paras', 'paras_front.png', 'paras_back.png', 'paras.ogg', 1, 'Bicho', 'Planta'),
(47, 'Parasect', 'parasect_front.png', 'parasect_back.png', 'parasect.ogg', 2, 'Bicho', 'Planta'),
(48, 'Venonat', 'venonat_front.png', 'venonat_back.png', 'venonat.ogg', 1, 'Bicho', 'Veneno'),
(49, 'Venomoth', 'venomoth_front.png', 'venomoth_back.png', 'venomoth.ogg', 2, 'Bicho', 'Veneno');

/*Insercion de datos a la tabla pokemon*/
/* Inserción de datos en la tabla Pokemon */
INSERT INTO Pokemon (
    id_pokemon, id_entrenador, num_pokedex, nombre, mote, vitalidad, ataque, defensa,
    ataque_especial, defensa_especial, velocidad, nivel, movimientos, fertilidad,
    sexo, tipo1, tipo2, estado, equipo
) VALUES
    (1, 1, 1, 'Bulbasaur', NULL, 45, 49, 49, 65, 65, 45, 5, 'Placaje, Gruñido, Drenadoras', 10, 'H', 'Planta', 'Veneno', 'Saludable', 1),
    (2, 1, 2, 'Ivysaur', NULL, 60, 62, 63, 80, 80, 60, 16, 'Placaje, Gruñido, Hoja Afilada', 10, 'M', 'Planta', 'Veneno', 'Saludable', 1),
    (3, 1, 3, 'Venusaur', NULL, 80, 82, 83, 100, 100, 80, 32, 'Látigo Cepa, Hoja Afilada, Drenadoras', 10, 'H', 'Planta', 'Veneno', 'Saludable', 1),
    (4, 1, 4, 'Charmander', NULL, 39, 52, 43, 60, 50, 65, 5, 'Ascuas, Gruñido, Arañazo', 10, 'M', 'Fuego', NULL, 'Saludable', 2),
    (5, 1, 5, 'Charmeleon', NULL, 58, 64, 58, 80, 65, 80, 16, 'Ascuas, Garra Dragón, Gruñido', 10, 'H', 'Fuego', NULL, 'Saludable', 2),
    (6, 1, 6, 'Charizard', NULL, 78, 84, 78, 109, 85, 100, 36, 'Lanzallamas, Vuelo, Garra Dragón', 10, 'M', 'Fuego', 'Volador', 'Saludable', 2),
    (7, 1, 7, 'Squirtle', NULL, 44, 48, 65, 50, 64, 43, 5, 'Burbuja, Placaje, Refugio', 10, 'H', 'Agua', NULL, 'Saludable', 3),
    (8, 1, 8, 'Wartortle', NULL, 59, 63, 80, 65, 80, 58, 16, 'Burbuja, Mordisco, Cascada', 10, 'M', 'Agua', NULL, 'Saludable', 3),
    (9, 1, 9, 'Blastoise', NULL, 79, 83, 100, 85, 105, 78, 36, 'Hidrobomba, Mordisco, Cascada', 10, 'H', 'Agua', NULL, 'Saludable', 3),
    (10, 1, 10, 'Caterpie', NULL, 45, 30, 35, 20, 20, 45, 5, 'Placaje, Disparo Demora', 10, 'M', 'Bicho', NULL, 'Saludable', 4),
    (11, 1, 11, 'Metapod', NULL, 50, 20, 55, 25, 25, 30, 7, 'Fortaleza', 10, 'H', 'Bicho', NULL, 'Saludable', 4),
    (12, 1, 12, 'Butterfree', NULL, 60, 45, 50, 90, 80, 70, 10, 'Tornado, Polvo Veneno, Confusión', 10, 'M', 'Bicho', 'Volador', 'Saludable', 4),
    (13, 1, 13, 'Weedle', NULL, 40, 35, 30, 20, 20, 50, 5, 'Picotazo Veneno, Gruñido', 10, 'H', 'Bicho', 'Veneno', 'Saludable', 5),
    (14, 1, 14, 'Kakuna', NULL, 45, 25, 50, 25, 25, 35, 7, 'Fortaleza', 10, 'M', 'Bicho', 'Veneno', 'Saludable', 5),
    (15, 1, 15, 'Beedrill', NULL, 65, 90, 40, 45, 80, 75, 10, 'Ataque Furia, Doble Ataque, Picotazo Veneno', 10, 'H', 'Bicho', 'Veneno', 'Saludable', 5),
    (16, 1, 16, 'Pidgey', NULL, 40, 45, 40, 35, 35, 56, 5, 'Tornado, Ataque Arena', 10, 'M', 'Normal', 'Volador', 'Saludable', 6),
    (17, 1, 17, 'Pidgeotto', NULL, 63, 60, 55, 50, 50, 71, 18, 'Tornado, Ataque Arena, Golpe Aire', 10, 'H', 'Normal', 'Volador', 'Saludable', 6),
    (18, 1, 18, 'Pidgeot', NULL, 83, 80, 75, 70, 70, 101, 36, 'Tornado, Ataque Arena, Golpe Aire', 10, 'M', 'Normal', 'Volador', 'Saludable', 6),
    (19, 1, 19, 'Rattata', NULL, 30, 56, 35, 25, 35, 72, 5, 'Ataque Rápido, Placaje', 10, 'H', 'Normal', NULL, 'Saludable', 7),
    (20, 1, 20, 'Raticate', NULL, 55, 81, 60, 50, 70, 97, 20, 'Hipercolmillo, Ataque Rápido', 10, 'M', 'Normal', NULL, 'Saludable', 7),
    (21, 1, 21, 'Spearow', NULL, 40, 60, 30, 31, 31, 70, 5, 'Picotazo, Ataque Arena', 10, 'H', 'Normal', 'Volador', 'Saludable', 8),
    (22, 1, 22, 'Fearow', NULL, 65, 90, 65, 61, 61, 100, 20, 'Picotazo, Ataque Arena, Golpe Aire', 10, 'M', 'Normal', 'Volador', 'Saludable', 8),
    (23, 1, 23, 'Ekans', NULL, 35, 60, 44, 40, 54, 55, 5, 'Constricción, Picotazo Veneno', 10, 'H', 'Veneno', NULL, 'Saludable', 9),
    (24, 1, 24, 'Arbok', NULL, 60, 85, 69, 65, 79, 80, 22, 'Mordisco, Colmillo Veneno, Ácido', 10, 'M', 'Veneno', NULL, 'Saludable', 9), 
    (25, 1, 25, 'Pikachu', NULL, 35, 55, 40, 50, 50, 90, 5, 'Impactrueno, Ataque Rápido', 10, 'H', 'Eléctrico', NULL, 'Saludable', 9),
    (26, 1, 26, 'Raichu', NULL, 60, 90, 55, 90, 80, 110, 20, 'Impactrueno, Chispazo, Cola Férrea', 10, 'M', 'Eléctrico', NULL, 'Saludable', 9),
    (27, 1, 27, 'Sandshrew', NULL, 50, 75, 85, 20, 30, 40, 5, 'Arañazo, Rizo Defensa', 10, 'H', 'Tierra', NULL, 'Saludable', 10),
    (28, 1, 28, 'Sandslash', NULL, 75, 100, 110, 45, 55, 65, 22, 'Arañazo, Rizo Defensa, Excavar', 10, 'M', 'Tierra', NULL, 'Saludable', 10),
    (29, 1, 29, 'Nidoran♀', NULL, 55, 47, 52, 40, 40, 41, 5, 'Gruñido, Doble Patada', 10, 'H', 'Veneno', NULL, 'Saludable', 11),
    (30, 1, 30, 'Nidorina', NULL, 70, 62, 67, 55, 55, 56, 16, 'Doble Patada, Mordisco', 10, 'M', 'Veneno', NULL, 'Saludable', 11),
    (31, 1, 31, 'Nidoqueen', NULL, 90, 92, 87, 75, 85, 76, 36, 'Terremoto, Golpe Cuerpo', 10, 'H', 'Veneno', 'Tierra', 'Saludable', 11),
    (32, 1, 32, 'Nidoran♂', NULL, 46, 57, 40, 40, 40, 50, 5, 'Gruñido, Doble Patada', 10, 'M', 'Veneno', NULL, 'Saludable', 12),
    (33, 1, 33, 'Nidorino', NULL, 61, 72, 57, 55, 55, 65, 16, 'Doble Patada, Mordisco', 10, 'H', 'Veneno', NULL, 'Saludable', 12),
    (34, 1, 34, 'Nidoking', NULL, 81, 102, 77, 85, 75, 85, 36, 'Terremoto, Megacuerno', 10, 'M', 'Veneno', 'Tierra', 'Saludable', 12),
    (35, 1, 35, 'Clefairy', NULL, 70, 45, 48, 60, 65, 35, 5, 'Canto, Doble Bofetón', 10, 'H', 'Hada', NULL, 'Saludable', 13),
    (36, 1, 36, 'Clefable', NULL, 95, 70, 73, 95, 90, 60, 20, 'Rayo, Puño Fuego', 10, 'M', 'Hada', NULL, 'Saludable', 13),
    (37, 1, 37, 'Vulpix', NULL, 38, 41, 40, 50, 65, 65, 5, 'Ascuas, Gruñido', 10, 'H', 'Fuego', NULL, 'Saludable', 14),
    (38, 1, 38, 'Ninetales', NULL, 73, 76, 75, 81, 100, 100, 20, 'Lanzallamas, Hipnosis', 10, 'M', 'Fuego', NULL, 'Saludable', 14),
    (39, 1, 39, 'Jigglypuff', NULL, 115, 45, 20, 45, 25, 20, 5, 'Canto, Destructor', 10, 'H', 'Normal', 'Hada', 'Saludable', 15),
    (40, 1, 40, 'Wigglytuff', NULL, 140, 70, 45, 85, 50, 45, 20, 'Rayo Hielo, Puño Trueno', 10, 'M', 'Normal', 'Hada', 'Saludable', 15),
    (41, 1, 41, 'Zubat', NULL, 40, 45, 35, 30, 40, 55, 5, 'Supersónico, Mordisco', 10, 'H', 'Veneno', 'Volador', 'Saludable', 16),
    (42, 1, 42, 'Golbat', NULL, 75, 80, 70, 65, 75, 90, 22, 'Ala de Acero, Chupavidas', 10, 'M', 'Veneno', 'Volador', 'Saludable', 16),
    (43, 1, 43, 'Oddish', NULL, 45, 50, 55, 75, 65, 30, 5, 'Absorber, Drenadoras', 10, 'H', 'Planta', 'Veneno', 'Saludable', 17),
    (44, 1, 44, 'Gloom', NULL, 60, 65, 70, 85, 75, 40, 18, 'Ácido, Somnífero', 10, 'M', 'Planta', 'Veneno', 'Saludable', 17),
    (45, 1, 45, 'Vileplume', NULL, 75, 80, 85, 110, 90, 50, 36, 'Polvo Veneno, Solar Rayo', 10, 'H', 'Planta', 'Veneno', 'Saludable', 17),
    (46, 1, 46, 'Paras', NULL, 35, 70, 55, 45, 55, 25, 5, 'Arañazo, Espora', 10, 'M', 'Bicho', 'Planta', 'Saludable', 18),
    (47, 1, 47, 'Parasect', NULL, 60, 95, 80, 60, 80, 30, 24, 'Somnífero, Gigadrenado', 10, 'H', 'Bicho', 'Planta', 'Saludable', 18),
    (48, 1, 48, 'Venonat', NULL, 60, 55, 50, 40, 55, 45, 5, 'Supersónico, Confusión', 10, 'M', 'Bicho', 'Veneno', 'Saludable', 19),
    (49, 1, 49, 'Venomoth', NULL, 70, 65, 60, 90, 75, 90, 31, 'Danza Caos, Psicorrayo', 10, 'H', 'Bicho', 'Veneno', 'Saludable', 19
);




CREATE TABLE Movimiento ( 
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY, 
    nom_movimiento VARCHAR(20) NOT NULL,  
    nivel_aprendizaje INT NOT NULL,  
    pp_max INT,  
    tipo ENUM('ATAQUE', 'ESTADO', 'MEJORA'),  -- ENUM es mejor que CHECK en MySQL
    potencia INT, 
    tipo_mov VARCHAR(20), 
    estado VARCHAR(20), 
    turnos INT,  
    mejora VARCHAR(20),  
    num INT
) ENGINE=InnoDB;


/*Insercion de datos a la tabla movimientos*/
INSERT INTO Movimiento (id_movimiento, nom_movimiento, nivel_aprendizaje, pp_max, tipo, potencia, tipo_mov, estado, turnos, mejora, num) VALUES
    (1, 'Placaje', 1, 35, 'ATAQUE', 40, 'Físico', NULL, NULL, NULL, 1), 
    (2, 'Gruñido', 1, 40, 'ESTADO', NULL, 'Estado', NULL, NULL, NULL, 2),  
    (3, 'Drenadoras', 1, 10, 'ATAQUE', 20, 'Especial', NULL, NULL, NULL, 3),  
    (4, 'Látigo Cepa', 1, 10, 'ATAQUE', 45, 'Físico', NULL, NULL, NULL, 4),  
    (5, 'Ascuas', 1, 25, 'ATAQUE', 40, 'Especial', NULL, NULL, NULL, 5), 
    (6, 'Arañazo', 1, 35, 'ATAQUE', 40, 'Físico', NULL, NULL, NULL, 6), 
    (7, 'Lanzallamas', 1, 15, 'ATAQUE', 90, 'Especial', NULL, NULL, NULL, 7), 
    (8, 'Burbuja', 1, 30, 'ATAQUE', 40, 'Especial', NULL, NULL, NULL, 8), 
    (9, 'Refugio', 1, 40, 'ESTADO', NULL, 'Estado', NULL, NULL, NULL, 9), 
    (10, 'Hidrobomba', 1, 5, 'ATAQUE', 110, 'Especial', NULL, NULL, NULL, 10), 
    (11, 'Tornado', 1, 35, 'ATAQUE', 40, 'Especial', NULL, NULL, NULL, 11), 
    (12, 'Ataque Arena', 1, 15, 'ESTADO', NULL, 'Estado', NULL, NULL, NULL, 12), 
    (13, 'Golpe Aire', 1, 20, 'ATAQUE', 75, 'Físico', NULL, NULL, NULL, 13), 
    (14, 'Hipercolmillo', 1, 15, 'ATAQUE', 80, 'Físico', NULL, NULL, NULL, 14), 
    (15, 'Picotazo', 1, 35, 'ATAQUE', 35, 'Físico', NULL, NULL, NULL, 15), 
    (16, 'Constricción', 1, 20, 'ATAQUE', 10, 'Físico', NULL, NULL, NULL, 16), 
    (17, 'Impactrueno', 1, 15, 'ATAQUE', 40, 'Especial', NULL, NULL, NULL, 17), 
    (18, 'Chispazo', 1, 20, 'ATAQUE', 65, 'Especial', NULL, NULL, NULL, 18), 
    (19, 'Cola Férrea', 1, 15, 'ATAQUE', 100, 'Físico', NULL, NULL, NULL, 19), 
    (20, 'Puño Trueno', 1, 15, 'ATAQUE', 75, 'Físico', NULL, NULL, NULL, 20), 
    (21, 'Terremoto', 1, 10, 'ATAQUE', 100, 'Físico', NULL, NULL, NULL, 21), 
    (22, 'Megacuerno', 1, 10, 'ATAQUE', 120, 'Físico', NULL, NULL, NULL, 22), 
    (23, 'Canto', 1, 15, 'ESTADO', NULL, 'Estado', NULL, NULL, NULL, 23), 
    (24, 'Doble Bofetón', 1, 10, 'ATAQUE', 15, 'Físico', NULL, NULL, NULL, 24), 
    (25, 'Rayo', 1, 15, 'ATAQUE', 90, 'Especial', NULL, NULL, NULL, 25), 
    (26, 'Puño Fuego', 1, 15, 'ATAQUE', 75, 'Físico', NULL, NULL, NULL, 26), 
    (27, 'Rayo Hielo', 1, 10, 'ATAQUE', 90, 'Especial', NULL, NULL, NULL, 27), 
    (28, 'Destructor', 1, 10, 'ATAQUE', 40, 'Físico', NULL, NULL, NULL, 28), 
    (29, 'Ala de Acero', 1, 25, 'ATAQUE', 70, 'Físico', NULL, NULL, NULL, 29), 
    (30, 'Chupavidas', 1, 10, 'ATAQUE', 80, 'Especial', NULL, NULL, NULL, 30), 
    (31, 'Ácido', 1, 30, 'ATAQUE', 40, 'Especial', NULL, NULL, NULL, 31), 
    (32, 'Somnífero', 1, 15, 'ESTADO', NULL, 'Estado', NULL, NULL, NULL, 32), 
    (33, 'Polvo Veneno', 1, 35, 'ESTADO', NULL, 'Estado', NULL, NULL, NULL, 33), 
    (34, 'Solar Rayo', 1, 10, 'ATAQUE', 120, 'Especial', NULL, NULL, NULL, 34), 
    (35, 'Espora', 1, 15, 'ESTADO', NULL, 'Estado', NULL, NULL, NULL, 35), 
    (36, 'Gigadrenado', 1, 10, 'ATAQUE', 75, 'Especial', NULL, NULL, NULL, 36), 
    (37, 'Danza Caos', 1, 20, 'ESTADO', NULL, 'Estado', NULL, NULL, NULL, 37), 
    (38, 'Psicorrayo', 1, 10, 'ATAQUE', 65, 'Especial', NULL, NULL, NULL, 38), 
    (39, 'Rayo Aurora', 1, 20, 'ATAQUE', 65, 'Especial', NULL, NULL, NULL, 39), 
    (40, 'Hiperrayo', 1, 5, 'ATAQUE', 150, 'Especial', NULL, NULL, NULL, 40), 
    (41, 'Picoteo', 1, 20, 'ATAQUE', 35, 'Físico', NULL, NULL, NULL, 41),
    (42, 'Espejo', 1, 20, 'ESTADO', NULL, 'Estado', NULL, NULL, NULL, 42), 
    (43, 'Viento Cortante', 1, 10, 'ATAQUE', 80, 'Especial', NULL, NULL, NULL, 43), 
    (44, 'Cuchillada', 1, 20, 'ATAQUE', 70, 'Físico', NULL, NULL, NULL, 44), 
    (45, 'Tijera X', 1, 15, 'ATAQUE', 80, 'Físico', NULL, NULL, NULL, 45), 
    (46, 'Corte Furia', 1, 20, 'ATAQUE', 40, 'Físico', NULL, NULL, NULL, 46), 
    (47, 'Cuchilla Afilada', 1, 15, 'ATAQUE', 70, 'Físico', NULL, NULL, NULL, 47), 
    (48, 'Tajo Umbrío', 1, 15, 'ATAQUE', 70, 'Físico', NULL, NULL, NULL, 48), 
    (49, 'Golpe Aéreo', 1, 5, 'ATAQUE', 140, 'Físico', NULL, NULL, NULL, 49);

UPDATE pokemon
SET equipo = 1
WHERE id_pokemon IN (1, 2, 3, 4, 5, 6);

--ALTER TABLE Entrenadores ADD pokeballs INT DEFAULT 0;
--
--/*Para guardar el login se añaden usuario y contraseña a Entrenadores*/
--ALTER TABLE Entrenadores
--ADD COLUMN usuario VARCHAR(50),
--ADD COLUMN contraseña VARCHAR(100);

ALTER TABLE Entrenadores MODIFY COLUMN id_entrenador INT NOT NULL AUTO_INCREMENT;

ALTER TABLE Entrenadores
MODIFY COLUMN equipo_principal VARCHAR(255) DEFAULT NULL,
MODIFY COLUMN equipo_secundario VARCHAR(255) DEFAULT NULL;
