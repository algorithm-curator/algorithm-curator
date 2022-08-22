/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-explicit-any */
/* eslint-disable @typescript-eslint/no-non-null-assertion */
import React, { useEffect, useState } from "react";
import ReactLoading from "react-loading";
import ProblemAllList from "components/problem/ProblemAllList";
import ProblemListTab from "components/problem/ProblemListTab";
import { getListProblems } from "apis/problem";
import { API_TOKEN } from "Utils/localStorageKeys";
import {
	ChartLink,
	Container,
	Title,
	TitleChartWrapper,
	MoreButton,
	ToastMessage,
} from "./styles";

function ProblemList() {
	const apiToken = localStorage.getItem(API_TOKEN);
	const [problems, setProblems] = useState<any[]>();
	const [page, setPage] = useState<number>(1);
	const [filterStatus, setFilterStatus] = useState<number | null>(null);
	const [recentProblems, setRecentProblems] = useState<number>(0);
	const getProblems = async (
		paging: boolean,
		state: number | null,
		stateChange: boolean
	) => {
		// paging이 true면 페이징 하기
		if (stateChange) setPage(1);
		// eslint-disable-next-line no-nested-ternary
		await getListProblems(apiToken, state, stateChange ? 0 : paging ? page : 0)
			.then((res) => {
				if (paging) {
					setPage(page + 1);
					setProblems([...problems!, ...res.data.response]);
				} else {
					setProblems(res.data.response);
				}
				setRecentProblems(res.data.response.length);
			})
			.catch((err) => {
				alert("에러가 발생했습니다.");
				// console.log(err);
			});
	};
	useEffect(() => {
		getProblems(false, null, false);
	}, []);

	return (
		<Container>
			<TitleChartWrapper>
				<Title>나의 문제 목록</Title>
				<ChartLink to="/mychart">📊차트로 파악하기</ChartLink>
			</TitleChartWrapper>
			<ProblemAllList
				setFilterStatus={setFilterStatus}
				getProblems={getProblems}
			/>
			{problems ? (
				problems.map((problem: any) => {
					return (
						<ProblemListTab
							key={problem.id}
							problemInfo={problem}
							getProblems={getProblems}
							filterStatus={filterStatus}
						/>
					);
				})
			) : (
				<div
					style={{
						display: "flex",
						justifyContent: "center",
						alignItems: "center",
						width: "100wh",
						height: "50vh",
					}}
				>
					<ReactLoading type="spin" width="5%" />
				</div>
			)}
			<div
				style={{
					width: "100%",
					display: "flex",
					flexDirection: "column",
					justifyContent: "center",
					alignItems: "center",
					marginTop: "1rem",
				}}
			>
				{problems ? (
					<MoreButton
						onClick={(e) => {
							getProblems(true, filterStatus, false);
						}}
					>
						더보기
					</MoreButton>
				) : null}
				<div style={{ height: "2rem" }}>
					{problems && !recentProblems ? (
						<ToastMessage>가져올 문제가 없습니다.</ToastMessage>
					) : null}
				</div>
			</div>
		</Container>
	);
}

export default ProblemList;
