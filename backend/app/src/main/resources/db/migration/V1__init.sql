CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    source_url TEXT NOT NULL,
    image_url TEXT NOT NULL,
    event_year INT NOT NULL,
    event_date TEXT NOT NULL
);

CREATE INDEX idx_events_date ON events(event_date);