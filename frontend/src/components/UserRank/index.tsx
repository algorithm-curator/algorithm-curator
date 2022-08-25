/* eslint-disable @typescript-eslint/no-explicit-any */
import React from "react";
import { UserInfoWrapper, UserInfo } from "./styles";

function UserRank({ rankInfo, ranking }: any) {
	return (
		<UserInfoWrapper>
			<UserInfo key={ranking + 1}>{ranking}</UserInfo>
			<UserInfo key={ranking + 2}>{rankInfo.nickname}</UserInfo>
			<UserInfo key={ranking + 3}>{rankInfo.solved_count}</UserInfo>
		</UserInfoWrapper>
	);
}

export default UserRank;
