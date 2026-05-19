USE hotel_management;

INSERT INTO hotels (name, address, phone, star_rating) VALUES
('Grand Tbilisi Hotel', 'Rustaveli Ave 1, Tbilisi', '+995322001234', 5),
('City Inn', 'Kostava St 45, Tbilisi', '+995322005678', 3);

INSERT INTO departments (hotel_id, name, location) VALUES
(1, 'Housekeeping', 'Floor 1'),
(1, 'Reception', 'Lobby'),
(2, 'Housekeeping', 'Floor 1'),
(2, 'Kitchen', 'Basement');

INSERT INTO staff (department_id, first_name, last_name, position, salary, hire_date, is_active) VALUES
(1, 'Nino', 'Kapanadze', 'Housekeeper', 800.00, '2021-03-15', 1),
(2, 'Giorgi', 'Beridze', 'Receptionist', 1200.00, '2020-06-01', 1),
(3, 'Mariam', 'Tsiklauri', 'Housekeeper', 850.00, '2022-01-10', 1),
(4, 'Luka', 'Jishkariani', 'Chef', 1500.00, '2019-11-20', 1);

INSERT INTO room_types (name, capacity, price_per_night) VALUES
('Single', 1, 80.00),
('Double', 2, 140.00),
('Suite', 2, 300.00),
('Deluxe', 4, 220.00);

INSERT INTO rooms (hotel_id, room_type_id, room_number, floor, is_available) VALUES
(1, 1, 101, 1, 1),
(1, 2, 201, 2, 1),
(1, 3, 301, 3, 0),
(2, 1, 101, 1, 1),
(2, 4, 202, 2, 1);

INSERT INTO amenities (name, is_complimentary) VALUES
('WiFi', 1),
('Minibar', 0),
('Safe', 1),
('Bathtub', 0),
('City View', 1);

INSERT INTO room_amenities (room_id, amenity_id) VALUES
(1, 1), (1, 3),
(2, 1), (2, 2), (2, 3),
(3, 1), (3, 2), (3, 3), (3, 4), (3, 5),
(4, 1),
(5, 1), (5, 4);

INSERT INTO guests (first_name, last_name, email, phone, passport_number, date_of_birth) VALUES
('Anna', 'Muller', 'anna.muller@email.com', '+49301234567', 'DE1234567', '1990-04-22'),
('James', 'Smith', 'james.smith@email.com', '+12025550190', 'US9876543', '1985-09-10'),
('Sofia', 'Rossi', 'sofia.rossi@email.com', '+390612345678', 'IT4567890', '1995-01-30'),
('Nikoloz', 'Akhvlediani', 'n.akh@email.com', '+995599123456', 'GE1122334', '1992-07-15');

INSERT INTO bookings (guest_id, room_id, check_in, check_out, created_at, is_paid) VALUES
(1, 1, '2025-06-01', '2025-06-05', '2025-05-10 14:00:00', 1),
(2, 2, '2025-06-10', '2025-06-15', '2025-05-15 09:30:00', 0),
(3, 3, '2025-07-01', '2025-07-07', '2025-06-01 11:00:00', 1),
(4, 4, '2025-06-20', '2025-06-22', '2025-06-05 16:00:00', 0);

INSERT INTO services (booking_id, name, price, ordered_at) VALUES
(1, 'Room Service', 25.00, '2025-06-02 20:00:00'),
(1, 'Laundry', 15.00, '2025-06-03 09:00:00'),
(2, 'Airport Pickup', 40.00, '2025-06-10 15:00:00'),
(3, 'Spa Treatment', 120.00, '2025-07-02 11:00:00'),
(4, 'Room Service', 30.00, '2025-06-21 19:00:00');