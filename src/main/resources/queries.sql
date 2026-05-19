USE hotel_management;

UPDATE bookings SET is_paid = 1 WHERE id = 2;

UPDATE rooms SET is_available = 0 WHERE id = 4;

UPDATE staff SET salary = salary * 1.10 WHERE department_id = 2;

UPDATE guests SET phone = '+49309999999' WHERE id = 1;

UPDATE hotels SET star_rating = 4 WHERE id = 2;

UPDATE room_types SET price_per_night = 95.00 WHERE name = 'Single';

UPDATE staff SET is_active = 0 WHERE id = 3;

UPDATE departments SET location = 'Floor 2' WHERE id = 1;

UPDATE services SET price = 35.00 WHERE name = 'Room Service';

UPDATE hotels SET address = 'Rustaveli Ave 5, Tbilisi' WHERE id = 1;


DELETE FROM services WHERE id = 5;

DELETE FROM services WHERE booking_id = 4;

DELETE FROM room_amenities WHERE room_id = 4 AND amenity_id = 1;

DELETE FROM bookings WHERE is_paid = 0 AND id = 4;

DELETE FROM guests WHERE id NOT IN (SELECT DISTINCT guest_id FROM bookings);

DELETE FROM staff WHERE is_active = 0;

DELETE FROM room_types WHERE id NOT IN (SELECT DISTINCT room_type_id FROM rooms);

DELETE FROM amenities WHERE id NOT IN (SELECT DISTINCT amenity_id FROM room_amenities);

DELETE FROM room_amenities WHERE room_id = 5;

DELETE FROM departments WHERE id NOT IN (SELECT DISTINCT department_id FROM staff);


SELECT
    h.name AS hotel_name,
    h.star_rating,
    d.name AS department,
    s.first_name AS staff_first,
    s.last_name AS staff_last,
    s.position,
    rt.name AS room_type,
    rt.price_per_night,
    r.room_number,
    r.floor,
    r.is_available,
    a.name AS amenity,
    a.is_complimentary,
    g.first_name AS guest_first,
    g.last_name AS guest_last,
    g.email,
    b.check_in,
    b.check_out,
    b.is_paid,
    sv.name AS service,
    sv.price AS service_price,
    sv.ordered_at
FROM hotels h
JOIN departments d ON d.hotel_id = h.id
JOIN staff s ON s.department_id = d.id
JOIN rooms r ON r.hotel_id = h.id
JOIN room_types rt ON rt.id = r.room_type_id
JOIN room_amenities ra ON ra.room_id = r.id
JOIN amenities a ON a.id = ra.amenity_id
JOIN bookings b ON b.room_id = r.id
JOIN guests g ON g.id = b.guest_id
JOIN services sv ON sv.booking_id = b.id;


SELECT r.room_number, r.floor, b.check_in, b.check_out
FROM rooms r
INNER JOIN bookings b ON b.room_id = r.id;

SELECT r.room_number, r.is_available, b.check_in, b.check_out
FROM rooms r
LEFT JOIN bookings b ON b.room_id = r.id;

SELECT r.room_number, b.check_in, b.is_paid
FROM rooms r
RIGHT JOIN bookings b ON b.room_id = r.id;

SELECT g.first_name, g.last_name, b.check_in, b.check_out
FROM guests g
LEFT JOIN bookings b ON b.guest_id = g.id;

SELECT h.name AS hotel, d.name AS department
FROM hotels h
LEFT JOIN departments d ON d.hotel_id = h.id
UNION
SELECT h.name AS hotel, d.name AS department
FROM hotels h
RIGHT JOIN departments d ON d.hotel_id = h.id;


SELECT b.id AS booking_id, SUM(sv.price) AS total_revenue
FROM bookings b
JOIN services sv ON sv.booking_id = b.id
GROUP BY b.id;

SELECT h.name AS hotel, COUNT(r.id) AS room_count
FROM hotels h
JOIN rooms r ON r.hotel_id = h.id
GROUP BY h.id, h.name;

SELECT rt.name AS room_type, AVG(rt.price_per_night) AS avg_price
FROM room_types rt
JOIN rooms r ON r.room_type_id = rt.id
GROUP BY rt.id, rt.name;

SELECT d.name AS department, COUNT(s.id) AS staff_count
FROM departments d
JOIN staff s ON s.department_id = d.id
GROUP BY d.id, d.name;

SELECT b.id AS booking_id, COUNT(sv.id) AS service_count
FROM bookings b
JOIN services sv ON sv.booking_id = b.id
GROUP BY b.id;

SELECT d.name AS department, MAX(s.salary) AS max_salary
FROM departments d
JOIN staff s ON s.department_id = d.id
GROUP BY d.id, d.name;

SELECT r.room_number, COUNT(ra.amenity_id) AS amenity_count
FROM rooms r
JOIN room_amenities ra ON ra.room_id = r.id
GROUP BY r.id, r.room_number;


SELECT h.name AS hotel, COUNT(r.id) AS room_count
FROM hotels h
JOIN rooms r ON r.hotel_id = h.id
GROUP BY h.id, h.name
HAVING COUNT(r.id) > 2;

SELECT d.name AS department, COUNT(s.id) AS staff_count
FROM departments d
JOIN staff s ON s.department_id = d.id
GROUP BY d.id, d.name
HAVING COUNT(s.id) > 1;

SELECT rt.name AS room_type, AVG(rt.price_per_night) AS avg_price
FROM room_types rt
GROUP BY rt.id, rt.name
HAVING AVG(rt.price_per_night) > 100;

SELECT b.id AS booking_id, SUM(sv.price) AS total_services
FROM bookings b
JOIN services sv ON sv.booking_id = b.id
GROUP BY b.id
HAVING SUM(sv.price) > 30;

SELECT g.first_name, g.last_name, COUNT(b.id) AS booking_count
FROM guests g
JOIN bookings b ON b.guest_id = g.id
GROUP BY g.id, g.first_name, g.last_name
HAVING COUNT(b.id) > 1;

SELECT h.name AS hotel, AVG(s.salary) AS avg_salary
FROM hotels h
JOIN departments d ON d.hotel_id = h.id
JOIN staff s ON s.department_id = d.id
GROUP BY h.id, h.name
HAVING AVG(s.salary) > 1000;

SELECT r.room_number, COUNT(ra.amenity_id) AS amenity_count
FROM rooms r
JOIN room_amenities ra ON ra.room_id = r.id
GROUP BY r.id, r.room_number
HAVING COUNT(ra.amenity_id) > 3;