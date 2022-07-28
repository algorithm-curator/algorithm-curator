import React from "react";
import { useNavigate } from "react-router-dom";
import { getLogout } from "apis/auth";
import { isLoggedState } from "stores/Auth";
import { useRecoilState } from "recoil";
import { API_TOKEN, KAKAO_ACCESS_TOKEN } from "Utils/localStorageKeys";
import { Container, Button, LogoutButton } from "./styles";

function LogOn() {
	const [isLogged, setIsLogged] = useRecoilState(isLoggedState);
	const navigate = useNavigate();

	const onClickLogout = () => {
		(async () => {
			const kakakoToken = localStorage.getItem(KAKAO_ACCESS_TOKEN);
			const apiToken = localStorage.getItem(API_TOKEN);
			await getLogout(apiToken, kakakoToken)
				.then((res) => {
					if (res.status === 200) {
						localStorage.removeItem(KAKAO_ACCESS_TOKEN);
						localStorage.removeItem(API_TOKEN);
						setIsLogged(false);
						navigate("/");
					}
				})
				.catch((err) => {
					if (err.response.status === 401) {
						alert("토큰이 만료되었습니다. 다시 로그인해주세요.");
						setIsLogged(false);
						localStorage.removeItem(API_TOKEN);
						localStorage.removeItem(KAKAO_ACCESS_TOKEN);
						navigate("/");
					}
				});
		})();
	};

	return (
		<Container>
			<Button to="/rank">Ranking</Button>
			<Button to="/problemList">Problem List</Button>
			<Button to="/mychart">Chart</Button>
			<Button to="/mypage">My Page</Button>
			<LogoutButton onClick={onClickLogout}>Logout</LogoutButton>
		</Container>
	);
}

export default LogOn;
