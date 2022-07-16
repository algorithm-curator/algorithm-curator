import React, { useState } from "react";

import ProblemAllList from "Components/Problem/ProblemAllList";
import ProblemListTab from "Components/Problem/ProblemListTab";
import { ChartLink, Container, Title, TitleChartWrapper } from "./styles";

function ProblemList() {
	// const [selectedProblem, setSelectedProblem] = useState<string>("");

	const onClickProblem = (e: any) => {
		console.log(e.target);
	};

	return (
		<Container>
			<TitleChartWrapper>
				<Title>나의 문제 목록</Title>
				<ChartLink to="/chart">📊차트로 파악하기</ChartLink>
			</TitleChartWrapper>
			<ProblemAllList />
			<ProblemListTab onClick={onClickProblem} />
		</Container>
	);
}

export default ProblemList;
