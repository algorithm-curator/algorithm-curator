import React from "react";

import ProblemAllList from "components/problem/ProblemAllList";
import ProblemListTab from "components/problem/ProblemListTab";
import { ChartLink, Container, Title, TitleChartWrapper } from "./styles";

function ProblemList() {
	return (
		<Container>
			<TitleChartWrapper>
				<Title>나의 문제 목록</Title>
				<ChartLink to="/chart">📊차트로 파악하기</ChartLink>
			</TitleChartWrapper>
			<ProblemAllList />
			<ProblemListTab />
		</Container>
	);
}

export default ProblemList;
