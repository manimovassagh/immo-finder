-- Drop existing tables if they exist
DROP TABLE IF EXISTS property CASCADE;

-- Create new schema
CREATE TABLE addresses (
    id UUID PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    house_number VARCHAR(20) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL DEFAULT 'DE'
);

CREATE TABLE apartments_for_rent (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price_per_month NUMERIC(10, 2) NOT NULL,
    area NUMERIC(6, 2) NOT NULL,
    rooms INT NOT NULL,
    bathrooms INT DEFAULT 1,
    floor INT,
    available_from DATE,
    is_furnished BOOLEAN DEFAULT FALSE,
    address_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_apartment_address FOREIGN KEY (address_id)
        REFERENCES addresses(id)
        ON DELETE CASCADE
);

CREATE TABLE apartment_photos (
    id UUID PRIMARY KEY,
    apartment_id UUID NOT NULL,
    url TEXT NOT NULL,
    position INT DEFAULT 0,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_apartment_photo FOREIGN KEY (apartment_id)
        REFERENCES apartments_for_rent(id)
        ON DELETE CASCADE
);

-- Add comments for documentation
COMMENT ON TABLE addresses IS 'Stores address information for rental properties';
COMMENT ON TABLE apartments_for_rent IS 'Stores detailed information about apartments available for rent';
COMMENT ON TABLE apartment_photos IS 'Stores photos associated with rental apartments'; 