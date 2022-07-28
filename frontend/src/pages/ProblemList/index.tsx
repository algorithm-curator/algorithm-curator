import React, { useEffect, useState } from "react";
import ReactLoading from "react-loading";
import ProblemAllList from "components/problem/ProblemAllList";
import ProblemListTab from "components/problem/ProblemListTab";
import { getListProblems } from "apis/problem";
import { API_TOKEN } from "Utils/localStorageKeys";
import { ChartLink, Container, Title, TitleChartWrapper } from "./styles";

function ProblemList() {
	const apiToken = localStorage.getItem(API_TOKEN);
	const [problems, setProblems] = useState<any[]>();
	const [page, setPage] = useState<number>(0);
	const [filterStatus, setFilterStatus] = useState<number | null>(null);
	const getProblems = async (paging: boolean, state: number | null) => {
		// paging이 true면 페이징 하기
		await getListProblems(apiToken, state, page)
			.then((res) => {
				setProblems(res.data.response);
				if (paging) setPage(page + 1);
			})
			.catch((err) => {
				alert("에러가 발생했습니다.");
				// console.log(err);
			});
	};
	useEffect(() => {
		getProblems(false, null);
	}, []);

	return (
		<Container>
			<TitleChartWrapper>
				<Title>나의 문제 목록</Title>
				<ChartLink to="/chart">📊차트로 파악하기</ChartLink>
			</TitleChartWrapper>
			<ProblemAllList
				setFilterStatus={setFilterStatus}
				getProblems={getProblems}
			/>
			{problems ? (
				problems.map((problem: any) => {
					return (
						<ProblemListTab
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
		</Container>
	);
}

export default ProblemList;
