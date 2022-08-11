/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useState } from "react";
import { Container, ProblemNav, Status } from "./styles";

function ProblemAllList({ setFilterStatus, getProblems }: any) {
	const onClickStatus = (e: any) => {
		setFilterStatus(e.target.value);
		getProblems(null, e.target.value === 0 ? null : e.target.value, true);
	};

	return (
		<Container>
			<ProblemNav>
				<Status onClick={onClickStatus} value={0}>
					전체 목록
				</Status>
				<Status onClick={onClickStatus} value={1}>
					풀이 미완료
				</Status>
				<Status onClick={onClickStatus} value={2}>
					풀이 완료
				</Status>
			</ProblemNav>
		</Container>
	);
}

export default ProblemAllList;
