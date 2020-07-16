import React from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";
import Home from "./component/Home/Home";
import Statement from "./component/Statement/Statement";
import {Badge, Col, Container, Nav, Navbar, Row} from "react-bootstrap";

export default function App() {
    return (
        <Router>
            <Container fluid="xl">
                <Row>
                    <Col>
                        <Navbar bg="light" expand="xl">
                            <Navbar.Brand>
                                <h3>fake Statement Processor Uploader</h3>
                            </Navbar.Brand>
                            <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                            <Navbar.Collapse id="basic-navbar-nav">
                                <Nav className="mr-auto">
                                    <Nav.Link href="/"><h3>Home</h3></Nav.Link>
                                    <Nav.Link href="/about"><h3>About</h3></Nav.Link>
                                    <Nav.Link href="/statement"><h3>Statement Upload</h3></Nav.Link>
                                </Nav>
                            </Navbar.Collapse>
                        </Navbar>
                    </Col>
                </Row>
                <Row><Col>&nbsp;</Col></Row>
                <Row>
                    <Switch>
                        <Route path="/about">
                            <About/>
                        </Route>
                        <Route path="/statement">
                            <Statement/>
                        </Route>
                        <Route path="/">
                            <Home/>
                        </Route>
                    </Switch>
                </Row>
            </Container>
        </Router>
    );
}

function About() {
    return <Container fluid="md">
        <Row><Col>&nbsp;</Col></Row>
        <Row>
            <Col>
                <h1><Badge variant="secondary">About</Badge></h1>
            </Col>
        </Row>
        <Row><Col>&nbsp;</Col></Row>
        <Row>
            <Col>
                <div className="sectionbody">
                    <div className="paragraph">
                        <h4>Made by Giona Granchelli</h4>
                    </div>
                </div>
            </Col>
        </Row>
    </Container>;
}
