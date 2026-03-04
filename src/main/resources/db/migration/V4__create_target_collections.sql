CREATE TABLE target_collections (
                                    id UUID PRIMARY KEY,
                                    owner_user_id UUID NOT NULL,
                                    name VARCHAR(120) NOT NULL,
                                    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_target_collections_owner_user_id
    ON target_collections(owner_user_id);

CREATE TABLE target_collection_members (
                                           collection_id UUID NOT NULL REFERENCES target_collections(id) ON DELETE CASCADE,
                                           target_id UUID NOT NULL REFERENCES targets(id) ON DELETE CASCADE,
                                           added_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                                           PRIMARY KEY (collection_id, target_id)
);

CREATE INDEX idx_tcm_target_id ON target_collection_members(target_id);