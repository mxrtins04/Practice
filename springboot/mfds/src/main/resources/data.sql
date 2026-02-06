-- Debug: This should appear in logs
SELECT 'Loading default data...';
-- Insert 1 default fuel attendant
INSERT INTO fuel_attendants (id, name) VALUES (1, 'Default Attendant');

-- Insert 3 default dispensers
INSERT INTO dispensers (id) VALUES (1), (2), (3);
