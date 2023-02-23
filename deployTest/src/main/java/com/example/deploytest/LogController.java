package com.example.deploytest;

@RestController
@RequestMapping("/log")
public class LogController {
    private final LogProducer logProducer;

    public LogController(LogProducer logProducer) {
        this.logProducer = logProducer;
    }

    @PostMapping
    public ResponseEntity<Void> produce(@RequestBody String message) {
        logProducer.send(message);
        return ResponseEntity.accepted().build();
    }
}
