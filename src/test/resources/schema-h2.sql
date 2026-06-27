CREATE TABLE hotels (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(100) NOT NULL,
    address    VARCHAR(255) NOT NULL,
    phone      VARCHAR(20)  NOT NULL,
    star_rating INT         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE departments (
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    hotel_id BIGINT      NOT NULL,
    name     VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    PRIMARY KEY (id),
    CONSTRAINT fk_departments_hotel FOREIGN KEY (hotel_id) REFERENCES hotels (id)
);

CREATE TABLE staff (
    id            BIGINT         NOT NULL AUTO_INCREMENT,
    department_id BIGINT         NOT NULL,
    first_name    VARCHAR(50)    NOT NULL,
    last_name     VARCHAR(50)    NOT NULL,
    position      VARCHAR(100)   NOT NULL,
    salary        DECIMAL(10, 2) NOT NULL,
    hire_date     DATE           NOT NULL,
    is_active     BOOLEAN        NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    CONSTRAINT fk_staff_department FOREIGN KEY (department_id) REFERENCES departments (id)
);

CREATE TABLE room_types (
    id               BIGINT         NOT NULL AUTO_INCREMENT,
    name             VARCHAR(50)    NOT NULL,
    capacity         INT            NOT NULL,
    price_per_night  DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE rooms (
    id           BIGINT  NOT NULL AUTO_INCREMENT,
    hotel_id     BIGINT  NOT NULL,
    room_type_id BIGINT  NOT NULL,
    room_number  INT     NOT NULL,
    floor        INT     NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    CONSTRAINT fk_rooms_hotel     FOREIGN KEY (hotel_id)     REFERENCES hotels (id),
    CONSTRAINT fk_rooms_room_type FOREIGN KEY (room_type_id) REFERENCES room_types (id)
);

CREATE TABLE amenities (
    id               BIGINT      NOT NULL AUTO_INCREMENT,
    name             VARCHAR(100) NOT NULL,
    is_complimentary BOOLEAN     NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
);

CREATE TABLE room_amenities (
    room_id    BIGINT NOT NULL,
    amenity_id BIGINT NOT NULL,
    PRIMARY KEY (room_id, amenity_id),
    CONSTRAINT fk_room_amenities_room    FOREIGN KEY (room_id)    REFERENCES rooms (id),
    CONSTRAINT fk_room_amenities_amenity FOREIGN KEY (amenity_id) REFERENCES amenities (id)
);

CREATE TABLE guests (
    id              BIGINT      NOT NULL AUTO_INCREMENT,
    first_name      VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE,
    phone           VARCHAR(20),
    passport_number VARCHAR(30) NOT NULL UNIQUE,
    date_of_birth   DATE        NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE bookings (
    id         BIGINT   NOT NULL AUTO_INCREMENT,
    guest_id   BIGINT   NOT NULL,
    room_id    BIGINT   NOT NULL,
    check_in   DATE     NOT NULL,
    check_out  DATE     NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_paid    BOOLEAN  NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    CONSTRAINT fk_bookings_guest FOREIGN KEY (guest_id) REFERENCES guests (id),
    CONSTRAINT fk_bookings_room  FOREIGN KEY (room_id)  REFERENCES rooms (id)
);

CREATE TABLE services (
    id         BIGINT         NOT NULL AUTO_INCREMENT,
    booking_id BIGINT         NOT NULL,
    name       VARCHAR(100)   NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    ordered_at DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_services_booking FOREIGN KEY (booking_id) REFERENCES bookings (id)
);
