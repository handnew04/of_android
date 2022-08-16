package kr.onekey.of.model

data class TokenInfo(
   val code: Int = 200,
   val accessToken: String? = null,
   val refreshToken: String? = null
) {
}