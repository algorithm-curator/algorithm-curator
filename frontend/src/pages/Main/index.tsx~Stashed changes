import React, { useEffect } from "react";
import axiosInstance from "utils/axiosInstance";
import { Container, Title, DrawButton } from "./styles";

function Main() {
	useEffect(() => {
		axiosInstance.get("/api/heartbeat").then((res) => {
			console.log(res);
		});
	}, []);

	return (
		<Container>
			<Title>매일 알고리즘 문제를 고르는 게 귀찮다면?</Title>
			<DrawButton>🔘</DrawButton>
		</Container>
	);
}

export default Main;
