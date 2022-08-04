import React, { useEffect, useState } from "react";
import { API_TOKEN } from "Utils/localStorageKeys";
import { getRankings } from "apis/rankings";
import axios from "axios";
import {
	Container,
	Title,
	MenuWrapper,
	Menu,
	UserInfoWrapper,
	UserInfo,
} from "./styles";

function Rank() {
	const apiToken = localStorage.getItem(API_TOKEN);
	const [page, setPage] = useState(0);
	useEffect(() => {
		(async () => {
			await getRankings(apiToken, page)
				.then((res) => {
					console.log(res);
				})
				.catch((err) => {
					console.log(err);
				});
		})();
	}, []);

	return (
		<Container>
			<Title>랭킹</Title>
			<MenuWrapper>
				<Menu>순위</Menu>
				<Menu>닉네임</Menu>
				<Menu>푼 문제/정답률</Menu>
			</MenuWrapper>
			<UserInfoWrapper>
				<UserInfo key={1}>1</UserInfo>
				<UserInfo key={2}>nickname</UserInfo>
				<UserInfo key={3}>140</UserInfo>
			</UserInfoWrapper>
		</Container>
	);
}

export default Rank;
