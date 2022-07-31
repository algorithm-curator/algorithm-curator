import styled from "@emotion/styled";
import { Link } from "react-router-dom";

export const Container = styled.div`
	display: flex;
	flex-direction: row;
`;

export const Button = styled(Link)`
	background-color: transparent;
	cursor: pointer;
	color: white;
	margin-right: 2rem;
	text-decoration: none;
	:hover {
		text-decoration: underline;
	}
`;
