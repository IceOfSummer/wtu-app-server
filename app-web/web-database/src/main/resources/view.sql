DROP VIEW IF EXISTS user_info_view;
CREATE VIEW user_info_view AS
SELECT
    user.user_id AS user_id,
    user.username AS username,
    user.wtu_username AS wtu_username,
    user.status AS status,
    user.credit AS credit,
    user.avatar AS avatar,
    wtu_user_info.name AS name,
    wtu_user_info.bedroom AS bedroom
FROM user, wtu_user_info
WHERE user.wtu_username = wtu_user_info.wtu_username;
