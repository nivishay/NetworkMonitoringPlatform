CREATE TABLE targets (
                         id UUID PRIMARY KEY,
                         owner_user_id UUID NOT NULL,
                         url VARCHAR(255) NOT NULL,
                         type VARCHAR(20) NOT NULL,
                         created_at TIMESTAMP NOT NULL,

                         CONSTRAINT fk_targets_owner
                             FOREIGN KEY (owner_user_id)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE
);

CREATE INDEX idx_targets_owner_user_id ON targets(owner_user_id);