/* eslint-disable @typescript-eslint/no-explicit-any */
/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-non-null-assertion */
import React, { useEffect, useState, useRef } from "react";
import ReactLoading from "react-loading";
import ProblemAllList from "components/problem/ProblemAllList";
import ProblemListTab from "components/problem/ProblemListTab";
import { getListProblems } from "apis/problem";
import { API_TOKEN } from "Utils/localStorageKeys";
import { ChartLink, Container, Title, TitleChartWrapper } from "./styles";

function ProblemList() {
	const apiToken = localStorage.getItem(API_TOKEN);
	const [problems, setProblems] = useState<any[]>();
	const [page, setPage] = useState<number>(1);
	const [filterStatus, setFilterStatus] = useState<number | null>(null);
	const boxRef = useRef(null);
	const observerRef = React.useRef<IntersectionObserver>();
	const getProblems = async (
		paging: boolean,
		state: number | null,
		stateChange: boolean
	) => {
		console.log(
			"paging :",
			paging,
			"| state :",
			state,
			"| stateChange :",
			stateChange
		);
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
			})
			.catch((err) => {
				alert("에러가 발생했습니다.");
				// console.log(err);
			});
	};
	const intersectionObserver = (entries: any, io: any) => {
		entries.forEach((entry: any) => {
			if (entry.isIntersecting) {
				// 관찰하고 있는 entry가 화면에 보여지는 경우
				io.unobserve(entry.target); // entry 관찰 해제
				getProblems(true, filterStatus, false); // 데이터 가져오기
			}
		});
	};
	useEffect(() => {
		getProblems(false, null, false);
	}, []);

	useEffect(() => {
		observerRef.current = new IntersectionObserver(intersectionObserver);
		// eslint-disable-next-line no-unused-expressions
		boxRef.current && observerRef.current.observe(boxRef.current);
	}, [problems]);

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
				problems.map((problem: any, index) => {
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
			{problems ? <div ref={boxRef} /> : null}
		</Container>
	);
}

export default ProblemList;
