package com.example.cloneddit.clone.vote;

import java.util.Arrays;

public enum VoteType {
    DOWNVOTE(-1), UPVOTE(1);

    private int direction;

    VoteType(int direction) {
    }

    public static VoteType look(Integer direction){
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Vote not found"));
    }

    public Integer getDirection(){
        return direction;
    }
}
