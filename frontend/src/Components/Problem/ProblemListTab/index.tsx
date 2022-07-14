import React from "react";
import { Container, Title, SolveLevelWrapper, Level, Origin } from "./styles";

type ProblemListTabProps = {
	onClick: (e: any) => void;
};

function ProblemListTab({ onClick }: ProblemListTabProps) {
	return (
		<Container onClick={onClick}>
			<Title>[백준] 문제 제목</Title>
			<SolveLevelWrapper>
				<Level>level 1</Level>
				<Origin>백준</Origin>
			</SolveLevelWrapper>
		</Container>
	);
}

export default ProblemListTab;
