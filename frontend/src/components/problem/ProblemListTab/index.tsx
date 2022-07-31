import React from "react";
import { Container, Title, SolveLevelWrapper, Level, Origin } from "./styles";

function ProblemListTab() {
	return (
		<Container>
			<Title>[백준] 문제 제목</Title>
			<SolveLevelWrapper>
				<Level>level 1</Level>
				<Origin>백준</Origin>
			</SolveLevelWrapper>
		</Container>
	);
}

export default ProblemListTab;
