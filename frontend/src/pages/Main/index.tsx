/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { isLoggedState } from "stores/Auth";
import { useRecoilState } from "recoil";
import { Container, Title, DrawButton } from "./styles";

function Main() {
	const [isLogged, setIsLogged] = useRecoilState(isLoggedState);
	const navigate = useNavigate();

	const onClickDraw = () => {
		if (isLogged) navigate("/problem");
		else alert("로그인을 해주세요.");
	};

	return (
		<Container>
			<Title>매일 알고리즘 문제를 고르는 게 귀찮다면?</Title>
			<DrawButton onClick={onClickDraw}>🔘</DrawButton>
		</Container>
	);
}

export default Main;
