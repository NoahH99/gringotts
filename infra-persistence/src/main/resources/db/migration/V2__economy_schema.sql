-- =============================================================================
-- V2: Economy schema
-- =============================================================================

-- ---------------------------------------------------------------------------
-- 1. Postgres enums
-- ---------------------------------------------------------------------------

CREATE TYPE account_type AS ENUM ('WALLET', 'BANK');
CREATE TYPE earning_source AS ENUM ('MESSAGE', 'VOICE');

-- event_type on ledger is plain TEXT so new earning sources need zero DDL.

-- ---------------------------------------------------------------------------
-- 2. users
-- ---------------------------------------------------------------------------

CREATE TABLE users
(
    id           BIGINT PRIMARY KEY,
    username     TEXT        NOT NULL,
    global_name  TEXT,
    avatar_hash  TEXT,
    global_admin BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ---------------------------------------------------------------------------
-- 3. guilds
-- ---------------------------------------------------------------------------

CREATE TABLE guilds
(
    id         BIGINT PRIMARY KEY,
    name       TEXT        NOT NULL,
    icon_hash  TEXT,
    owner_id   BIGINT, -- intentionally not FK (bootstrap ordering)
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ---------------------------------------------------------------------------
-- 4. guild_economy_config
-- ---------------------------------------------------------------------------

CREATE TABLE guild_economy_config
(
    guild_id                BIGINT PRIMARY KEY REFERENCES guilds (id) ON DELETE CASCADE,
    text_award_min          INTEGER       NOT NULL DEFAULT 3,
    text_award_max          INTEGER       NOT NULL DEFAULT 10,
    text_award_chance       NUMERIC(5, 4) NOT NULL DEFAULT 0.7500,
    text_cooldown_secs      INTEGER       NOT NULL DEFAULT 60,
    voice_tick_secs         INTEGER       NOT NULL DEFAULT 120,
    voice_award_min         INTEGER       NOT NULL DEFAULT 1,
    voice_award_max         INTEGER       NOT NULL DEFAULT 2,
    voice_min_duration_secs INTEGER       NOT NULL DEFAULT 600,
    deposit_fee_bps         INTEGER       NOT NULL DEFAULT 0,
    withdraw_fee_bps        INTEGER       NOT NULL DEFAULT 0,
    created_at              TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMPTZ   NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_gec_text_range CHECK (text_award_min >= 0 AND text_award_max >= text_award_min),
    CONSTRAINT chk_gec_chance CHECK (text_award_chance BETWEEN 0 AND 1),
    CONSTRAINT chk_gec_text_cd CHECK (text_cooldown_secs >= 0),
    CONSTRAINT chk_gec_voice_tick CHECK (voice_tick_secs > 0),
    CONSTRAINT chk_gec_voice_award_min CHECK (voice_award_min >= 0),
    CONSTRAINT chk_gec_voice_award_max CHECK (voice_award_max >= voice_award_min),
    CONSTRAINT chk_gec_voice_min_dur CHECK (voice_min_duration_secs >= 0),
    CONSTRAINT chk_gec_deposit_fee CHECK (deposit_fee_bps BETWEEN 0 AND 10000),
    CONSTRAINT chk_gec_withdraw_fee CHECK (withdraw_fee_bps BETWEEN 0 AND 10000)
);

-- ---------------------------------------------------------------------------
-- 5. guild_members
-- ---------------------------------------------------------------------------

CREATE TABLE guild_members
(
    id         BIGSERIAL PRIMARY KEY,
    guild_id   BIGINT      NOT NULL REFERENCES guilds (id) ON DELETE CASCADE,
    user_id    BIGINT      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    joined_at  TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_guild_members_guild_user UNIQUE (guild_id, user_id)
);

-- ---------------------------------------------------------------------------
-- 6. accounts
-- ---------------------------------------------------------------------------

CREATE TABLE accounts
(
    id           BIGSERIAL PRIMARY KEY,
    member_id    BIGINT       NOT NULL REFERENCES guild_members (id) ON DELETE CASCADE,
    account_type account_type NOT NULL,
    balance      BIGINT       NOT NULL DEFAULT 0,
    created_at   TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ  NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_accounts_member_type UNIQUE (member_id, account_type),
    CONSTRAINT chk_accounts_balance_non_neg CHECK (balance >= 0)
);

-- ---------------------------------------------------------------------------
-- 7. ledger_entries
-- ---------------------------------------------------------------------------

CREATE TABLE ledger_entries
(
    id              BIGSERIAL PRIMARY KEY,
    account_id      BIGINT      NOT NULL REFERENCES accounts (id) ON DELETE RESTRICT,
    amount          BIGINT      NOT NULL,
    balance_after   BIGINT      NOT NULL,
    event_type      TEXT        NOT NULL,
    idempotency_key TEXT UNIQUE,
    metadata        JSONB,
    actor_user_id   BIGINT      REFERENCES users (id) ON DELETE SET NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_ledger_amount_nonzero CHECK (amount <> 0)
);

-- ---------------------------------------------------------------------------
-- 8. earning_cooldowns
-- ---------------------------------------------------------------------------

CREATE TABLE earning_cooldowns
(
    member_id       BIGINT         NOT NULL REFERENCES guild_members (id) ON DELETE CASCADE,
    source          earning_source NOT NULL,
    last_awarded_at TIMESTAMPTZ    NOT NULL,

    PRIMARY KEY (member_id, source)
);

-- ---------------------------------------------------------------------------
-- 9. Indexes
-- ---------------------------------------------------------------------------

-- leaderboard: balance sort per guild + type
CREATE INDEX idx_accounts_member_type_bal
    ON accounts (member_id, account_type, balance DESC);

-- audit trail: time-ordered entries per account
CREATE INDEX idx_ledger_account_created
    ON ledger_entries (account_id, created_at DESC);

-- reporting: filter ledger by event type
CREATE INDEX idx_ledger_event_type_created
    ON ledger_entries (event_type, created_at DESC);

-- Allow efficient lookup of all guild memberships for a given user
CREATE INDEX idx_guild_members_user_id ON guild_members (user_id);
