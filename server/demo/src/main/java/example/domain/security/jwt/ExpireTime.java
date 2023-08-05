package example.domain.security.jwt;

public enum ExpireTime {
    REFRESH_COOKIE_EXPIRE_TIME(60 * 60 * 10),  // 리프레시 쿠키 만료 시간
    ACCESS_TOKEN_EXPIRE_TIME(1000 * 60 * 60 * 24),  // 액세스 토큰 만료 시간
    REFRESH_TOKEN_EXPIRE_TIME(1000 * 60 * 60 * 10);

    private final long time;

    ExpireTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
