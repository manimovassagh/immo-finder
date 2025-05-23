-- Insert mock data for testing purposes

-- Insert address
INSERT INTO address (id, street, house_number, postal_code, city, country)
VALUES 
  ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Sample Street', '123', '12345', 'Berlin', 'Germany');

-- Insert rent apartment
INSERT INTO rent_apartment (
  id, 
  title, 
  base_price, 
  additional_costs, 
  rooms, 
  furnished, 
  has_parking, 
  has_balcony, 
  has_storage, 
  size, 
  floor, 
  total_floors, 
  available_from, 
  description, 
  energy_certificate, 
  year_built, 
  property_type, 
  pets_allowed, 
  heating_type, 
  user_id, 
  elevator, 
  barrier_free, 
  created_at, 
  updated_at, 
  address_id
)
VALUES (
  'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 
  'Beautiful Apartment in Berlin', 
  1200.00, 
  200.00, 
  3, 
  true, 
  true, 
  true, 
  true, 
  85.5, 
  2, 
  5, 
  '2023-12-01', 
  'A beautiful and spacious apartment in the heart of Berlin. Perfect for a small family or professionals.', 
  true, 
  2010, 
  'APARTMENT', 
  true, 
  'GAS', 
  'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 
  true, 
  false, 
  CURRENT_TIMESTAMP, 
  CURRENT_TIMESTAMP, 
  'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'
);

-- Insert photos
INSERT INTO photo (id, file_name, url, position, apartment_id)
VALUES 
  ('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'd1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11_image1.jpg', '/images/d1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11_image1.jpg', 0, 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'),
  ('e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'e1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11_image2.jpg', '/images/e1eebc99-9c0b-4ef8-bb6d-6bb9bd380a11_image2.jpg', 1, 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11');
