import React from "react";

import ProblemTab from "Components/Problem/ProblemTab";
import ProblemDraw from "Components/Problem/ProblemDraw";
import { Container, ProblemListTextWrapper, ProblemListText } from "./styles";

function ProblemToday() {
	return (
		<Container>
			<ProblemDraw />
			<ProblemTab />
			<ProblemListTextWrapper>
				<ProblemListText>문제 목록 보러 가기</ProblemListText>
			</ProblemListTextWrapper>
		</Container>
	);
}

export default ProblemToday;
