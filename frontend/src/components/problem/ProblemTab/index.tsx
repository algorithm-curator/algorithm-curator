import React from "react";
import {
	Container,
	Title,
	SolveLevelWrapper,
	SolveStatus,
	Level,
} from "./styles";

function index() {
	return (
		<Container>
			<Title>[백준] 문제 제목</Title>
			<SolveLevelWrapper>
				<SolveStatus>
					<option>선택하지 않음</option>
					<option>미완료</option>
					<option>완료</option>
				</SolveStatus>
				<Level>level 1</Level>
			</SolveLevelWrapper>
		</Container>
	);
}

export default index;
