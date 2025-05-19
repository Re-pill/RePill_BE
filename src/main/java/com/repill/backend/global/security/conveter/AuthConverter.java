package com.repill.backend.global.security.conveter;

import com.repill.backend.global.security.authDTO.AuthResponseDTO;
import com.repill.backend.global.security.authDTO.KakaoProfile;
import com.repill.backend.member.entity.Member;
import com.repill.backend.member.entity.Rank;

public class AuthConverter {

    public static Member kakaoToMember(KakaoProfile kakaoProfile) {
        return Member.builder()
                .name(kakaoProfile.getKakaoNickname().getNickname())
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .ecoContribution(0)
                .level(1)
                .memberRank(Rank.LV1_BASIC)
                .build();
    }

    public static AuthResponseDTO.OAuthResponse toOAuthResponse(
            String accessToken, String refreshToken, Member member) {
        return AuthResponseDTO.OAuthResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .memberId(member.getId())
                .build();
    }

    public static AuthResponseDTO.TokenResponse toTokenRefreshResponse(
            String accessToken, String refreshToken) {
        return AuthResponseDTO.TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
