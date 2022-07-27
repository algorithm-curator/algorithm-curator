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

		(async () => {
			await getLogin(kakaoToken)
				.then((res) => {
					if (res.status === 401) {
						setIsLogged(false);
						localStorage.removeItem(KAKAO_ACCESS_TOKEN);
						localStorage.removeItem(API_TOKEN);
					} else if (res.status === 200) {
						localStorage.setItem(API_TOKEN, res.data.response.api_token);
						setIsLogged(true);
					}
				})
				.catch((err) => {
					setIsLogged(false);
					localStorage.removeItem(KAKAO_ACCESS_TOKEN);
					localStorage.removeItem(API_TOKEN);
				});
		})();
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
