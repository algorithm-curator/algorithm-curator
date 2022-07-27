import styled from "@emotion/styled";
import { Link } from "react-router-dom";

export const Container = styled.div`
	display: flex;
	flex-direction: row;
	align-items: center;
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

export const LogoutButton = styled.a`
	background-color: black;
	border-radius: 5px;
	color: white;
	text-decoration: none;
	padding: 0.8rem 1rem;
	cursor: pointer;
`;
