CREATE TABLE ticket_interactions (
  id uuid primary key,
  ticket_id uuid null,
  sent_by_user_id uuid not null,
  message text not null,
  status varchar(200) not null,
  created_by uuid null,
  created_at timestamp not null,
  updated_by uuid null,
  updated_at timestamp null
)