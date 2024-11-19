// SPDX-License-Identifier: SDGLR
pragma solidity ^0.8.0;

contract VotingContract {
    address public owner;
    mapping(address => bool) public hasVoted;
    mapping(bytes32 => uint256) public votesCount;

    event VoteSubmitted(address indexed voter, bytes32 indexed option);

    modifier onlyOwner() {
        require(msg.sender == owner, "Only the owner can call this function");
        _;
    }

    constructor() {
        owner = msg.sender;
    }

    function submitVote(bytes32 option) external {
        require(!hasVoted[msg.sender], "You have already voted");
        
        // Increase the vote count for the selected option
        votesCount[option] += 1;

        // Mark the user as voted
        hasVoted[msg.sender] = true;

        emit VoteSubmitted(msg.sender, option);
    }

    function getVoteCount(bytes32 option) external view returns (uint256) {
        return votesCount[option];
    }

    function getUserVoteStatus() external view returns (bool) {
        return hasVoted[msg.sender];
    }

    function getOwner() external view returns (address) {
        return owner;
    }
}