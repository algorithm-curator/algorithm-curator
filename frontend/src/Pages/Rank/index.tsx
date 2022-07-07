import React from "react";
import {
	Container,
	Title,
	MenuWrapper,
	Menu,
	UserInfoWrapper,
	UserInfo,
} from "./styles";

function Rank() {
	return (
		<Container>
			<Title>랭킹</Title>
			<MenuWrapper>
				<Menu>순위</Menu>
				<Menu>닉네임</Menu>
				<Menu>푼 문제/정답률</Menu>
			</MenuWrapper>
			<UserInfoWrapper>
				<UserInfo>1</UserInfo>
				<UserInfo>nickname</UserInfo>
				<UserInfo>140</UserInfo>
			</UserInfoWrapper>
		</Container>
	);
}

export default Rank;