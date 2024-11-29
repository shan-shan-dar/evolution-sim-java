import json
import matplotlib.pyplot as plt
import matplotlib.animation as animation

def render_simulation(json_file, output_file=None):
    # Load JSON data
    with open(json_file, 'r') as f:
        data = json.load(f)

    steps = data['steps']
    maxX = data['width']
    maxY = data['height']

    # Initialize plot
    fig, ax = plt.subplots()
    ax.set_xlim(0, maxX)
    ax.set_ylim(0, maxY)
    scatter = ax.scatter([], [], c=[], s=100)

    def init():
        scatter.set_offsets([])
        scatter.set_color([])
        return scatter,

    def update(frame):
        dots = steps[frame]['dots']
        positions = [(dot['x'], dot['y']) for dot in dots]
        scatter.set_offsets(positions)
        ax.set_title(f"Simulation Step: {steps[frame]['simStep']}")
        return scatter,

    ani = animation.FuncAnimation(
        fig, update, frames=len(steps), init_func=init, blit=True, interval=500
    )

    # Save animation or show it
    if output_file:
        ani.save(output_file, fps=1, writer='imagemagick')
    else:
        plt.show()

# Example usage
render_simulation('build/generated/worldviz/JSON/gen0.json')
