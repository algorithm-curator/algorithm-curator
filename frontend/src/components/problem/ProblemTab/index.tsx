import React from "react";
import {
	Container,
	Title,
	SolveLevelWrapper,
	SolveStatus,
	Level,
} from "./styles";

function index({ problemInfo }: any) {
	return (
		<Container>
			<Title>
				[{problemInfo.quiz_platform}] {problemInfo.title}
			</Title>
			<SolveLevelWrapper>
				<SolveStatus>
					<option>선택하지 않음</option>
					<option>미완료</option>
					<option>완료</option>
				</SolveStatus>
				<Level>{problemInfo.quiz_level}</Level>
			</SolveLevelWrapper>
		</Container>
	);
}

export default index;
