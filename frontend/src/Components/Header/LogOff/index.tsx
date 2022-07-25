import React from "react";
import { KAKAO_AUTH_URL } from "Utils/KakaoLogin/OAuth";
import { Container, LoginButton } from "./styles";

function LogOff() {
	return (
		<Container>
			<LoginButton href={KAKAO_AUTH_URL}>Login</LoginButton>
		</Container>
	);
}

export default LogOff;
