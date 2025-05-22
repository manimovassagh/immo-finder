-- Address Table
CREATE TABLE address (
    id UUID PRIMARY KEY,
    street VARCHAR(255),
    house_number VARCHAR(50),
    postal_code VARCHAR(20),
    city VARCHAR(100),
    country VARCHAR(100)

);

-- Rent Apartment Table
CREATE TABLE rent_apartment (
    id UUID PRIMARY KEY,
    title VARCHAR(255),
    base_price NUMERIC NOT NULL,
    additional_costs NUMERIC NOT NULL,
    rooms INTEGER NOT NULL,
    furnished BOOLEAN NOT NULL,
    has_parking BOOLEAN NOT NULL,
    has_balcony BOOLEAN NOT NULL,
    has_storage BOOLEAN NOT NULL,
    size DOUBLE PRECISION,
    floor INTEGER,
    total_floors INTEGER,
    available_from DATE,
    description TEXT,
    country VARCHAR(100),
    energy_certificate BOOLEAN,
    year_built INTEGER,
    property_type VARCHAR(50),
    pets_allowed BOOLEAN,
    heating_type VARCHAR(50),
    user_id UUID NOT NULL,
    elevator BOOLEAN,
    barrier_free BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    address_id UUID UNIQUE,
    CONSTRAINT fk_rent_apartment_address
        FOREIGN KEY (address_id) REFERENCES address(id)
);

-- Photo Table
CREATE TABLE photo (
    id UUID PRIMARY KEY,
    file_name VARCHAR(255),
    url VARCHAR(500),
    position INTEGER,
    apartment_id UUID,
    CONSTRAINT fk_photo_apartment
        FOREIGN KEY (apartment_id) REFERENCES rent_apartment(id)
);