/* eslint-disable @typescript-eslint/no-non-null-assertion */
/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useEffect, useState } from "react";
import { API_TOKEN } from "Utils/localStorageKeys";
import { getRankings } from "apis/rankings";
import UserRank from "components/UserRank";
import {
	Container,
	Title,
	MenuWrapper,
	Menu,
	MoreButton,
	ToastMessage,
} from "./styles";

function Rank() {
	const apiToken = localStorage.getItem(API_TOKEN);
	const [page, setPage] = useState(0);
	const [rankInfos, setRankInfos] = useState<any[]>();
	const [recentRanks, setRecentRanks] = useState(0);
	const onClickMore = async () => {
		await getRankings(apiToken, page)
			.then((res) => {
				setPage(page + 1);
				setRankInfos([...rankInfos!, ...res.data.response]);
				setRecentRanks(res.data.response.length);
			})
			.catch((err) => {
				console.log(err);
			});
	};
	useEffect(() => {
		(async () => {
			await getRankings(apiToken, page)
				.then((res) => {
					setPage(page + 1);
					setRankInfos(res.data.response);
					setRecentRanks(res.data.response.length);
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
				<Menu>푼 문제</Menu>
			</MenuWrapper>
			{rankInfos
				? rankInfos.map((rankInfo, index) => {
						return (
							<UserRank
								key={rankInfo.id}
								rankInfo={rankInfo}
								ranking={index + 1}
							/>
						);
				  })
				: null}
			<tr
				style={{
					width: "100%",
					display: "flex",
					justifyContent: "center",
					alignItems: "center",
					marginTop: "1rem",
				}}
			>
				{rankInfos ? (
					<MoreButton onClick={onClickMore}>더보기</MoreButton>
				) : null}
			</tr>
			<div
				style={{
					display: "flex",
					justifyContent: "center",
					alignItems: "center",
					height: "2rem",
				}}
			>
				{rankInfos && !recentRanks ? (
					<ToastMessage>가져올 랭크가 없습니다.</ToastMessage>
				) : null}
			</div>
		</Container>
	);
}

export default Rank;
