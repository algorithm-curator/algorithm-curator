import React, { useState } from "react";
import { Container, ProblemNav, Status } from "./styles";

function ProblemAllList() {
	const [problemStatus, setProblemStatus] = useState("전체 목록");

	const onClickStatus = (e: any) => {
		setProblemStatus(e.target.innerHTML);
	};

	return (
		<Container>
			<ProblemNav>
				<Status
					onClick={onClickStatus}
					// isSelected={problemStatus === "전체 목록"}
				>
					전체 목록
				</Status>
				<Status
					onClick={onClickStatus}
					// isSelected={problemStatus === "풀이 미완료"}
				>
					풀이 미완료
				</Status>
				<Status
					onClick={onClickStatus}
					// isSelected={problemStatus === "풀이 완료"}
				>
					풀이 완료
				</Status>
			</ProblemNav>
			<hr
				style={{ border: "0", width: "100%", borderTop: "1px solid black" }}
			/>
		</Container>
	);
}

export default ProblemAllList;
