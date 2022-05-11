package server;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArgumentParser {
    public Arguments parse(List<String> lines) {
        Arguments arguments = new Arguments();
        arguments.setNumberOfThreads(Integer.parseInt(lines.get(0)));
        arguments.setStartingNode(lines.get(1).charAt(0));

        for (int i = 2; i < lines.size(); i++) {
            List<Character> graphLine = Arrays.stream(lines.get(i).split(",\\s+"))
                    .map(str -> str.charAt(0))
                    .collect(Collectors.toList());

            arguments.getGraph().put(graphLine.get(0), graphLine.subList(1, graphLine.size()));
        }

        return arguments;
    }
}
