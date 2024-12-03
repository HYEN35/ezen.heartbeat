package kr.heartbeat.music.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("kr.heartbeat.mapper.music")  // 매퍼 인터페이스가 위치한 패키지를 스캔
@ComponentScan(basePackages = "kr.heartbeat.music.config")
public class AppConfig {
	@Bean
    public SpotifyAPI spotifyAPI() {
        return new SpotifyAPI();
    }
}
