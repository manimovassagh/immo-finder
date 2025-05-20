-- Add unique constraint on address fields
ALTER TABLE public.address
ADD CONSTRAINT uk_address_unique UNIQUE (street, number, postal_code, city, country); 