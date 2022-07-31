import React from "react";

import ProblemTab from "components/problem/ProblemTab";
import ProblemDraw from "components/problem/ProblemDraw";
import {
	Container,
	ProblemListTextWrapper,
	ProblemListText,
	ProblemStatusButton,
} from "./styles";

function ProblemToday() {
	return (
		<Container>
			<ProblemDraw />
			<ProblemTab />
			<ProblemListTextWrapper>
				<ProblemStatusButton>문제상태 수정완료</ProblemStatusButton>
				<ProblemListText to="/problemlist">
					모든 문제목록 보러가기
				</ProblemListText>
			</ProblemListTextWrapper>
		</Container>
	);
}

export default ProblemToday;
