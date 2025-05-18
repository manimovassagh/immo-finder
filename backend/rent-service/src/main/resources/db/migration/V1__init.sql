-- Initial schema setup
CREATE TABLE IF NOT EXISTS property (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(12,2) NOT NULL,
    area DECIMAL(10,2),
    bedrooms INT,
    bathrooms INT,
    address VARCHAR(255),
    city VARCHAR(100),
    postal_code VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Example comment for testing database connection
COMMENT ON TABLE property IS 'Table for storing rental property listings'; 