/* eslint-disable @typescript-eslint/no-non-null-assertion */
import React, { useEffect } from "react";
import { isLoggedState } from "stores/Auth";
import { useRecoilState } from "recoil";
import { KAKAO_ACCESS_TOKEN, API_TOKEN } from "Utils/localStorageKeys";
import { getLogin } from "apis/auth";
import { Container, HeaderWrapper } from "./styles";
import Logo from "./Logo";
import LogOff from "./LogOff";
import LogOn from "./LogOn";

function Header() {
	const [isLogged, setIsLogged] = useRecoilState(isLoggedState);
	useEffect(() => {
		const kakaoToken = localStorage.getItem(KAKAO_ACCESS_TOKEN)!;

		if (kakaoToken) {
			(async () => {
				await getLogin(kakaoToken)
					.then((res) => {
						setIsLogged(true);
						localStorage.setItem(API_TOKEN, res.data.response.api_token);
					})
					.catch((err) => {
						if (err.response.status === 401) {
							alert("토큰이 만료되었습니다. 다시 로그인해주세요.");
							setIsLogged(false);
							localStorage.removeItem(KAKAO_ACCESS_TOKEN);
							localStorage.removeItem(API_TOKEN);
						} else {
							console.log(err);
						}
					});
			})();
		}
	}, []);

	return (
		<Container>
			<HeaderWrapper>
				<Logo />
				{isLogged ? <LogOn /> : <LogOff />}
			</HeaderWrapper>
		</Container>
	);
}

export default Header;
