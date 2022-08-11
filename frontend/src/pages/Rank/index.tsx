/* eslint-disable @typescript-eslint/no-non-null-assertion */
/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useEffect, useState } from "react";
import { API_TOKEN } from "Utils/localStorageKeys";
import { getRankings } from "apis/rankings";
import UserRank from "components/UserRank";
import { Container, Title, MenuWrapper, Menu, MoreButton } from "./styles";

function Rank() {
	const apiToken = localStorage.getItem(API_TOKEN);
	const [page, setPage] = useState(0);
	const [rankInfos, setRankInfos] = useState<any[]>();
	const onClickMore = async () => {
		await getRankings(apiToken, page)
			.then((res) => {
				setPage(page + 1);
				setRankInfos([...rankInfos!, ...res.data.response]);
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
		</Container>
	);
}

export default Rank;
